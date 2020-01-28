package br.com.diegogusava.rabobankpoa.app.operator.domain;

import lombok.Data;

import java.util.List;

@Data
public class OperatorGroup {
    private String id;
    private String name;
    private List<String> operatorIds;
}