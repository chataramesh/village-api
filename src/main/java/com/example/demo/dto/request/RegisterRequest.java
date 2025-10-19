package com.example.demo.dto.request;

import com.example.demo.entity.Village;
import com.example.demo.enums.Role;

import lombok.Data;

@Data
public class RegisterRequest {

	private String name;
	private String email;
	private String phone;
	private String password;
	private Role role;
	private Village village;
}
