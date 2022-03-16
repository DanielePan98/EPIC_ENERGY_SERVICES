package it.epicode.be.energy;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
	
	@Autowired
	MockMvc mockMvc;

	@Test
	@WithAnonymousUser
	public void loginNoBody() throws Exception {
		this.mockMvc.perform(post("/auth/login")).andExpect(status().isBadRequest());
	}

	
	@Test
    public void utenteRealeGetTokenAndAuthentication() throws Exception {
        String username = "admin";
        String password = "admin";
        
        String body = "{\"userName\":\"" + username + "\", \"password\":\"" 
                      + password + "\"}";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk()).andReturn();
        String response = result.getResponse().getContentAsString();
        
      
}


}
