package com.backendframeworks.memeapi.services.memes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendframeworks.memeapi.models.Meme;
import com.backendframeworks.memeapi.repositories.MemeRepository;

@Service
public class GetAllMemesUseCase {
    
     @Autowired
     private MemeRepository memeRepository;

     public List<Meme> execute() {
        return memeRepository.findAll();
    }
}
