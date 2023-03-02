package com.example.bhatti.Services;

import com.example.bhatti.UserPojo.CabPojo;
import com.example.bhatti.entity.Cab;

import java.util.List;


public interface CabService {
    String save_cab_record(CabPojo cabPojo);
    List<Cab> findAll();
    void deleteById(Integer id);
}
