package com.viettel.jobfinder.modules.employer.controller;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.jobfinder.modules.employer.Employer;
import com.viettel.jobfinder.modules.employer.dto.EditEmployerBasicInfoRequestDto;
import com.viettel.jobfinder.modules.employer.dto.EmployerResponseDto;
import com.viettel.jobfinder.modules.employer.service.EmployerService;
import com.viettel.jobfinder.modules.user.service.UserService;
import com.viettel.jobfinder.shared.Utils;
import com.viettel.jobfinder.shared.annotation.CurrentUser;
import com.viettel.jobfinder.shared.annotation.EmployerPermission;
import com.viettel.jobfinder.shared.annotation.Public;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/employer")
@Tag(name = "Employer")
@Order(6)
public class EmployerController {
  @Autowired
  private EmployerService employerService;

  @GetMapping
  @Public
  @Operation(summary = "Get all employers")
  public ResponseEntity<List<EmployerResponseDto>> getEmployers(
      @RequestParam(name = "userEmployerId", required = false) Long userEmployerId,
      @RequestParam(name = "username", required = false) String username,
      @RequestParam(name = "fullName", required = false) String fullName,
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
    Page<Employer> employers = employerService.filterEmployer(userEmployerId, username, fullName, pageable);
    Function<Employer, EmployerResponseDto> converter = EmployerResponseDto::new;
    return Utils.buildPaginationResponse(employers, converter);
  }

  // @GetMapping("/info/{id}")
  // @Public
  // public ResponseEntity<EmployerResponseDto>
  // getEmployerInfoById(@PathVariable("id") long id) {
  // Employer employer = employerService.getEmployerInfo(id);
  // return new ResponseEntity<EmployerResponseDto>(new
  // EmployerResponseDto(employer), HttpStatus.OK);
  // }

  @PutMapping("/my-info")
  @EmployerPermission
  @Operation(summary = "Edit employer basic info")
  public ResponseEntity<EmployerResponseDto> editBasicInfo(@CurrentUser("id") long id,
      @Valid @RequestBody EditEmployerBasicInfoRequestDto editEmployerBasicInfoRequestDto) {
    Employer employer = employerService.editBasicInfo(id, editEmployerBasicInfoRequestDto);
    return new ResponseEntity<EmployerResponseDto>(new EmployerResponseDto(employer), HttpStatus.OK);
  }

  @GetMapping("/my-info")
  @EmployerPermission
  @Operation(summary = "Get employer info")
  public ResponseEntity<EmployerResponseDto> getEmployerInfo(@CurrentUser("id") long id) {
    Employer employer = employerService.getEmployerInfo(id);
    return new ResponseEntity<EmployerResponseDto>(new EmployerResponseDto(employer), HttpStatus.OK);
  }

}
