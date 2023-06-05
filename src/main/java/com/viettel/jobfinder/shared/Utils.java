package com.viettel.jobfinder.shared;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public class Utils {
  public static <T, U> ResponseEntity<List<U>> buildPaginationResponse(Page<T> data, Function<T, U> converter) {
    // Get page information from the data
    int pageNumber = data.getNumber();
    int pageSize = data.getSize();
    long totalElements = data.getTotalElements();

    // Create headers
    HttpHeaders headers = new HttpHeaders();
    headers.add("Page-Number", String.valueOf(pageNumber));
    headers.add("Page-Size", String.valueOf(pageSize));
    headers.add("Total-Elements", String.valueOf(totalElements));

    // Convert data to the desired response type
    List<U> responseData = data.stream().map(converter).collect(Collectors.toList());

    return ResponseEntity.ok()
        .headers(headers)
        .body(responseData);
  }
}
