package com.company.crm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * We will have our DTO for Customer.
 * I personally believed that its good to maintain one model (our Entity) to lessen bugs and maintenance.
 * Less code, less error prone.
 *
 * HOWEVER, we also need to sanitize what fields the users can access, hence the DTO.
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "Customer Details",
          description = "The data object that holds customer details")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDTO implements Serializable {

    private Long id;

    @ApiModelProperty(required = true)
    @NotNull(message = "NotNull.customerDTO.firstName")
    private String firstName;

    @ApiModelProperty(required = true)
    @NotNull(message = "NotNull.customerDTO.lastName")
    private String lastName;

    @ApiModelProperty(required = true)
    @NotNull(message = "NotNull.customerDTO.dateOfBirth")
    private Date dateOfBirth;

    private String homeAddress;

    private String officeAddress;

    private String emailAddress;
}
