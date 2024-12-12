package com.app.dictapp.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.dictapp.model.Dictionary;

public interface DictionaryRepo extends JpaRepository<Dictionary, Integer>{
	
	@Query("select d from Dictionary d where d.word=:word")
	Dictionary getByWord(@Param("word") String word);
	
}
