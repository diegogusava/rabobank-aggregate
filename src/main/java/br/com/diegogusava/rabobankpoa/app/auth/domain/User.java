package br.com.diegogusava.rabobankpoa.app.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    private String id;
    private String operatorId;
    private String username;
    private String password;
    private List<String> roles;

}
