package com.example.employeegcp.Entity;

import com.google.cloud.spring.data.spanner.core.mapping.PrimaryKey;
import com.google.cloud.spring.data.spanner.core.mapping.Table;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "employees") // GCP Spanner table name
public class Employee {

    @PrimaryKey
    private String id;
    private String name;
    private String email;
}
