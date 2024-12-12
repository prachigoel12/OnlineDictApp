package com.app.dictapp.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.dictapp.model.AdminLogin;

public interface AdminLoginRepo extends JpaRepository<AdminLogin, String>{
	
}
