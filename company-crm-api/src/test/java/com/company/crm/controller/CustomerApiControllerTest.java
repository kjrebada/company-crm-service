package com.company.crm.controller;

import com.company.crm.controller.secure.CustomerApiController;
import com.company.crm.model.CustomerDTO;
import com.company.crm.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CustomerApiControllerTest {

    @InjectMocks
    private CustomerApiController customerApiController;

    @Mock
    private CustomerService customerService;

    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @Before
    public void setup() {
        mapper = new ObjectMapper();
        MappingJackson2HttpMessageConverter jacksonMsgConverter = new MappingJackson2HttpMessageConverter();
        jacksonMsgConverter.setObjectMapper(mapper);
        mockMvc = MockMvcBuilders
                .standaloneSetup(customerApiController)
                .setMessageConverters(jacksonMsgConverter)
                .build();
    }

    protected String json(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    private CustomerDTO createMockCustomerDTO() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("first");
        customerDTO.setLastName("last");
        customerDTO.setDateOfBirth(new Date());
        return customerDTO;
    }

    @Test
    public void testAddCustomer() throws Exception {
        doNothing().when(customerService).updateCustomer(any(CustomerDTO.class));
        MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/secure/customer/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(createMockCustomerDTO()));

        checkResult(post);
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        doNothing().when(customerService).updateCustomer(any(CustomerDTO.class));
        MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/secure/customer/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(createMockCustomerDTO()));

        checkResult(post);
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        doNothing().when(customerService).deleteCustomerById(anyLong());
        MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/secure/customer/delete/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON);

        checkResult(post);
    }

    @Test
    public void testGetCustomer() throws Exception {
        when(customerService.getCustomerById(anyLong())).thenReturn(createMockCustomerDTO());
        MockHttpServletRequestBuilder get = MockMvcRequestBuilders.get("/secure/customer/get/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON);

        String rawResult = mockMvc.perform(get).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertNotNull(rawResult);

        CustomerDTO result = mapper.readValue(rawResult, CustomerDTO.class);

        assertEquals("first", result.getFirstName());
    }

    @Test
    public void testGetAllCustomer() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(createMockCustomerDTO()));
        MockHttpServletRequestBuilder get = MockMvcRequestBuilders.get("/secure/customers")
                .contentType(MediaType.APPLICATION_JSON);

        String rawResult = mockMvc.perform(get).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertNotNull(rawResult);

        List<CustomerDTO> result = mapper.readValue(rawResult, List.class);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    private void checkResult(MockHttpServletRequestBuilder post) throws Exception {
        String rawResult = mockMvc.perform(post).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertNotNull(rawResult);

        String result = mapper.readValue(rawResult, String.class);

        assertEquals("OK", result);
    }
}
