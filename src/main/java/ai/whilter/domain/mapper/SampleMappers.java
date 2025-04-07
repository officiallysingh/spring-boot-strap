package ai.whilter.domain.mapper;

import ai.whilter.common.jpa.RevisionRecord;
import ai.whilter.domain.SampleErrorTypes;
import ai.whilter.domain.model.City;
import ai.whilter.domain.model.Employee;
import ai.whilter.domain.model.State;
import ai.whilter.domain.model.dto.CityVM;
import ai.whilter.domain.model.dto.EmployeeVM;
import ai.whilter.domain.model.dto.StateVM;
import com.ksoot.problem.core.Problems;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.data.history.Revision;

@Mapper
public interface SampleMappers {

  SampleMappers INSTANCE = Mappers.getMapper(SampleMappers.class);

  Sort SORT_BY_NAME = Sort.by(Sort.Order.asc("name").ignoreCase());

  Comparator<State> STATE_BY_NAME_COMPARATOR =
      Comparator.comparing(state -> state.getName().toLowerCase());

  Comparator<City> CITY_BY_NAME_COMPARATOR =
      Comparator.comparing(city -> city.getName().toLowerCase());

  Function<List<Revision<Integer, Employee>>, List<RevisionRecord<Integer, Employee, EmployeeVM>>>
      EMPLOYEE_AUDIT_PAGE_TRANSFORMER =
          revisions ->
              revisions.stream()
                  .map(
                      revision ->
                          new RevisionRecord<>(revision, SampleMappers.INSTANCE::toEmployeeVM))
                  .toList();

  EmployeeVM toEmployeeVM(final Employee employee);

  // ---------- State, City and Area ----------
  default StateVM toStateViewModel(final State state, final String expand) {
    if (StringUtils.isEmpty(expand)) {
      return toStateSummaryViewModel(state);
    } else if (Objects.equals(expand, "cities")) {
      return stateViewModel(state);
    } else {
      throw Problems.newInstance(SampleErrorTypes.INVALID_STATE_EXPAND_HEADER)
          .detailArgs(expand)
          .throwAble();
    }
  }

  @Named("stateSummaryViewModel")
  @Mapping(target = "cities", ignore = true)
  StateVM toStateSummaryViewModel(final State state);

  @Mapping(target = "cities", qualifiedByName = "citySummaryViewModel")
  StateVM stateViewModel(final State state);

  default CityVM toCityViewModel(final City city, final List<String> expand) {
    if (CollectionUtils.isEmpty(expand)) {
      return citySummaryViewModel(city);
    }
    if (expand.contains("state") && expand.contains("areas")) {
      return cityViewModel(city);
    } else if (expand.contains("state")) {
      return cityWithStateViewModel(city);
    } else if (expand.contains("areas")) {
      return cityWithAreasViewModel(city);
    } else {
      throw Problems.newInstance(SampleErrorTypes.INVALID_CITY_EXPAND_HEADER)
          .detailArgs(expand)
          .throwAble();
    }
  }

  @Named("citySummaryViewModel")
  @Mapping(target = "state", ignore = true)
  CityVM citySummaryViewModel(final City city);

  @Named("cityWithStateViewModel")
  @Mapping(source = "state", target = "state", qualifiedByName = "stateSummaryViewModel")
  CityVM cityWithStateViewModel(final City city);

  @Named("cityWithAreasViewModel")
  @Mapping(source = "state", target = "state", ignore = true)
  CityVM cityWithAreasViewModel(final City city);

  @Mapping(source = "state", target = "state", qualifiedByName = "stateSummaryViewModel")
  CityVM cityViewModel(final City city);
}
