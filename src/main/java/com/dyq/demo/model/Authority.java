package com.dyq.demo.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class Authority extends BaseModel implements GrantedAuthority {
    private String name;
    @Override
    public String getAuthority() {
        return name;
    }

    public Authority(){
        setUpdatedAt(new Date());
        if(getId()==null)
            setCreatedAt(new Date());
    }
}