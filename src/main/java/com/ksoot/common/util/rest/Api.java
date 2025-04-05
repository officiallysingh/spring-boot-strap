package com.ksoot.common.util.rest;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@ApiResponses(
    value = {
      @ApiResponse(
          responseCode = ApiStatus.SC_401,
          description = "Unauthorized",
          content =
              @Content(examples = @ExampleObject(ApiConstants.UNAUTHORIZED_EXAMPLE_RESPONSE))),
      @ApiResponse(
          responseCode = ApiStatus.SC_403,
          description = "Forbidden",
          content = @Content(examples = @ExampleObject(ApiConstants.FORBIDDEN_EXAMPLE_RESPONSE))),
      @ApiResponse(
          responseCode = ApiStatus.SC_500,
          description = "Internal Server error",
          content =
              @Content(
                  examples = @ExampleObject(ApiConstants.INTERNAL_SERVER_ERROR_EXAMPLE_RESPONSE)))
    })
public interface Api {}
