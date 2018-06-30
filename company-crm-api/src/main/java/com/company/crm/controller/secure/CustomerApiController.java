package com.company.crm.controller.secure;

import com.company.crm.model.CustomerDTO;
import com.company.crm.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for Customer profile manipulation.
 *
 * To simplify we only need one endpoint for all channels.
 * Its not advisable to have the same controller calls for different type of channels,
 * of which it also access one database. At the same time we don't need to add new endpoints to support other channels.
 * So how are we going to differentiate? Users of the API need to pass CHANNEL_NAME=web|app in the request header.
 * This CHANNEL_NAME will then be verified if User have access to that channel.
 *
 */
@Api(tags = "Secure API for Customer operations")
@RestController
@RequestMapping("/secure")
public class CustomerApiController {

    @Autowired
    private CustomerService customerService;

    @ApiOperation(notes = "Add a new customer",
            value = "/secure/customer/add",
            produces = "application/json",
            consumes = "application/json")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization",
                            value = "Bearer {access-token}",
                            required = true,
                            dataType = "string",
                            paramType = "header"),
                    @ApiImplicitParam(name = "CHANNEL_NAME",
                            value = "authorized channel for user",
                            required = true,
                            dataType = "string",
                            paramType = "header")
            }
    )
    @PostMapping(value = "/customer/add")
    public final ResponseEntity<String> addCustomer(@RequestBody CustomerDTO customer) {
        customerService.updateCustomer(customer);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @ApiOperation(notes = "Delete a customer by id",
            value = "/secure/customer/delete/{id}",
            produces = "application/json",
            consumes = "application/json")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization",
                            value = "Bearer {access-token}",
                            required = true,
                            dataType = "string",
                            paramType = "header"),
                    @ApiImplicitParam(name = "CHANNEL_NAME",
                            value = "authorized channel for user",
                            required = true,
                            dataType = "string",
                            paramType = "header")
            }
    )
    @PostMapping(value = "/customer/delete/{id}")
    public final ResponseEntity<String> deleteCustomer(@PathVariable long id) {
        customerService.deleteCustomerById(id);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @ApiOperation(notes = "Get all customers",
            value = "/secure/customers",
            produces = "application/json",
            consumes = "application/json")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization",
                            value = "Bearer {access-token}",
                            required = true,
                            dataType = "string",
                            paramType = "header"),
                    @ApiImplicitParam(name = "CHANNEL_NAME",
                            value = "authorized channel for user",
                            required = true,
                            dataType = "string",
                            paramType = "header")
            }
    )
    @GetMapping(value = "/customers")
    public final ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @ApiOperation(notes = "Get a customer by id",
            value = "/secure/customer/add",
            produces = "application/json",
            consumes = "application/json")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization",
                            value = "Bearer {access-token}",
                            required = true,
                            dataType = "string",
                            paramType = "header"),
                    @ApiImplicitParam(name = "CHANNEL_NAME",
                            value = "authorized channel for user",
                            required = true,
                            dataType = "string",
                            paramType = "header")
            }
    )
    @GetMapping(value = "/customer/get/{id}")
    public final ResponseEntity<CustomerDTO> getCustomer(@PathVariable long id) {
        CustomerDTO customerDTO = customerService.getCustomerById(id);
        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    @ApiOperation(notes = "Update a customer",
            value = "/secure/customer/update",
            produces = "application/json",
            consumes = "application/json")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization",
                            value = "Bearer {access-token}",
                            required = true,
                            dataType = "string",
                            paramType = "header"),
                    @ApiImplicitParam(name = "CHANNEL_NAME",
                            value = "authorized channel for user",
                            required = true,
                            dataType = "string",
                            paramType = "header")
            }
    )
    @PostMapping(value = "/customer/update")
    public final ResponseEntity<String> updateCustomer(@RequestBody CustomerDTO customer) {
        customerService.updateCustomer(customer);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
