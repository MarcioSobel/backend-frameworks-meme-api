package com.backendframeworks.memeapi.services.memes;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendframeworks.memeapi.models.Meme;
import com.backendframeworks.memeapi.repositories.MemeRepository;

@Service
public class CreateMemeUseCase {
    
     @Autowired
     private MemeRepository memeRepository;

     public Meme execute(Meme meme) {
        meme.setCreatedAt(Instant.now());
        return memeRepository.save(meme);
    }

}
