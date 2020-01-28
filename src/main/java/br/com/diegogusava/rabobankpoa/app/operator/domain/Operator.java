package br.com.diegogusava.rabobankpoa.app.operator.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Operator {

    private String id;
    private String name;
    private Set<String> operatorGroupIds;

}

