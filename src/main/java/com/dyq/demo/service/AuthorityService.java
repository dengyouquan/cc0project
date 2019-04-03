package com.dyq.demo.service;


import com.dyq.demo.model.Authority;

import java.util.Optional;

public interface AuthorityService extends BaseModelService<Authority> {
    int getCount();
}
