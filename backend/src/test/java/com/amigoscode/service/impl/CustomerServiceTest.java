package com.amigoscode.service.impl;import com.amigoscode.entity.Customer;import com.amigoscode.entity.CustomerRegistrationRequest;import com.amigoscode.entity.CustomerUpdateRequest;import com.amigoscode.entity.Gender;import com.amigoscode.exception.DuplicateResourceException;import com.amigoscode.exception.RequestValidationException;import com.amigoscode.exception.ResourceNotFoundException;import com.amigoscode.repository.CustomerDao;import org.junit.jupiter.api.BeforeEach;import org.junit.jupiter.api.Test;import org.junit.jupiter.api.extension.ExtendWith;import org.mockito.ArgumentCaptor;import org.mockito.Mock;import org.mockito.junit.jupiter.MockitoExtension;import java.util.Optional;import static org.assertj.core.api.Assertions.assertThat;import static org.assertj.core.api.Assertions.assertThatThrownBy;import static org.mockito.Mockito.*;import static org.mockito.Mockito.verify;@ExtendWith(MockitoExtension.class) // this is a JUnit 5 annotationclass CustomerServiceTest {    @Mock    private CustomerDao customerDao;    private CustomerService underTest;    @BeforeEach    void setUp() {        underTest = new CustomerService(customerDao);    }    @Test    void getAllCustomers() {        // WHEN        underTest.getAllCustomers();        // THEN        verify(customerDao).selectAllCustomers();    }    @Test    void canGetCustomerById() {        // GIVEN        int id = 10;        Customer customer = new Customer(id, "James", "Bond", 35, Gender.MALE);        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));        // WHEN        Customer actual = underTest.getCustomerById(id);        // THEN        assertThat(actual).isEqualTo(customer);    }    @Test    void willThrowWhenGetCustomerReturnEmptyOptional() {        // GIVEN        int id = 10;        when(customerDao.selectCustomerById(id)).thenReturn(Optional.empty());        // WHEN        // THEN        assertThatThrownBy(() -> underTest.getCustomerById(id))                .isInstanceOf(ResourceNotFoundException.class)                .hasMessage("customer with id [%s] not found".formatted(id));    }    @Test    void addCustomer() {        // GIVEN        int id = 10;        String email = "bond@gmail.com";        when(customerDao.existsPersonWithEmail(email)).thenReturn(false);        CustomerRegistrationRequest request = new CustomerRegistrationRequest(                "James",                email,                35,                Gender.MALE);        // WHEN        underTest.addCustomer(request);        // THEN        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);        verify(customerDao).insertCustomer(customerArgumentCaptor.capture());        Customer capturedCustomer = customerArgumentCaptor.getValue();        assertThat(capturedCustomer.getId()).isNull();        assertThat(capturedCustomer.getName()).isEqualTo(request.name());        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());        assertThat(capturedCustomer.getAge()).isEqualTo(request.age());    }    @Test    void willThrowWhenEmailExistsWhileAddingAddingACustomer() {        // GIVEN        int id = 10;        String email = "bond@gmail.com";        when(customerDao.existsPersonWithEmail(email)).thenReturn(true);        CustomerRegistrationRequest request = new CustomerRegistrationRequest(                "James",                email,                35,                Gender.MALE);        // WHEN        assertThatThrownBy(() -> underTest.addCustomer(request))                .isInstanceOf(DuplicateResourceException.class)                .hasMessage("Email already taken");        // THEN        verify(customerDao, never()).insertCustomer(any());    }    @Test    void deleteCustomerById() {        // GIVEN        int id = 10;        when(customerDao.existsPersonWithId(id)).thenReturn(true);        // WHEN        underTest.deleteCustomerById(id);        // THEN        verify(customerDao).deleteCustomerById(id);    }    @Test    void willThrowDeleteCustomerByIdNotExists() {        // GIVEN        int id = 10;        when(customerDao.existsPersonWithId(id)).thenReturn(false);        // WHEN        assertThatThrownBy(() -> underTest.deleteCustomerById(id))                .isInstanceOf(ResourceNotFoundException.class)                .hasMessage("customer with id [%s] not found".formatted(id));        // THEN        verify(customerDao, never()).deleteCustomerById(id);    }    @Test    void canUpdateAllCustomersProperties() {        // GIVEN        int id = 10;        Customer customer = new Customer(id, "James", "Bond", 35, Gender.MALE);        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));        String newEmail = "peter@gmail.com";        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("Peter", newEmail, 33);        when(customerDao.existsPersonWithEmail(newEmail)).thenReturn(false);        // WHEN        underTest.updateCustomer(id, updateRequest);        // THEN        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());        Customer capturedCustomer = customerArgumentCaptor.getValue();        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.age());        assertThat(capturedCustomer.getEmail()).isEqualTo(updateRequest.email());    }    @Test    void canUpdateOnlyCustomerName() {        // GIVEN        int id = 10;        Customer customer = new Customer(id, "James", "Bond", 35, Gender.MALE);        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(                "Peter", null, null);        // WHEN        underTest.updateCustomer(id, updateRequest);        // THEN        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());        Customer capturedCustomer = customerArgumentCaptor.getValue();        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());    }    @Test    void canUpdateOnlyCustomerEmail() {        // GIVEN        int id = 10;        Customer customer = new Customer(id, "James", "Bond", 35, Gender.MALE);        when(customerDao.selectCustomerById(id))                .thenReturn(Optional.of(customer));        String newEmail = "peter@gmail.com";        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(                null, newEmail, null);        when(customerDao.existsPersonWithEmail(newEmail)).thenReturn(false);        // WHEN        underTest.updateCustomer(id, updateRequest);        // THEN        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());        Customer capturedCustomer = customerArgumentCaptor.getValue();        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());        assertThat(capturedCustomer.getEmail()).isEqualTo(newEmail);    }    @Test    void canUpdateOnlyCustomerAge() {        // GIVEN        int id = 10;        Customer customer = new Customer(id, "James", "Bond", 35, Gender.MALE);        when(customerDao.selectCustomerById(id))                .thenReturn(Optional.of(customer));        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(                null, null, 22);        // WHEN        underTest.updateCustomer(id, updateRequest);        // THEN        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());        Customer capturedCustomer = customerArgumentCaptor.getValue();        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.age());    }    @Test    void willThrowWhenTryingToUpdateCustomerEmailWhenAlreadyTaken() {        // GIVEN        int id = 10;        Customer customer = new Customer(id, "James", "Bond", 35, Gender.MALE);        when(customerDao.selectCustomerById(id))                .thenReturn(Optional.of(customer));        String newEmail = "peter@gmail.com";        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(                null, newEmail, null);        when(customerDao.existsPersonWithEmail(newEmail)).thenReturn(true);        // WHEN        assertThatThrownBy(() -> underTest.updateCustomer(id, updateRequest))                .isInstanceOf(DuplicateResourceException.class)                .hasMessage("Email already taken");        // THEN        verify(customerDao, never()).updateCustomer(any());    }    @Test    void willThrowWhenCustomerUpdateHasNoChanges() {        // GIVEN        int id = 10;        Customer customer = new Customer(id, "James", "Bond", 35, Gender.MALE);        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(                customer.getName(), customer.getEmail(), customer.getAge());        // WHEN        assertThatThrownBy(() -> underTest.updateCustomer(id, updateRequest))                .isInstanceOf(RequestValidationException.class)                .hasMessage("no data changes found");        // THEN        verify(customerDao, never()).updateCustomer(any());    }}