package com.backendframeworks.memeapi.services.memes;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendframeworks.memeapi.models.Meme;
import com.backendframeworks.memeapi.repositories.MemeRepository;

@Service
public class UpdateMemeUseCase {
     @Autowired
     private MemeRepository memeRepository;

      public Meme execute(UUID id, Meme memeDetails) {
        Meme meme = memeRepository.findById(id).orElse(null);
        if (meme != null) {
            meme.setDescription(memeDetails.getDescription());
            meme.setImgUrl(memeDetails.getImgUrl());
            meme.setUpdatedAt(Instant.now());
            return memeRepository.save(meme);
        }
        return null;
    }
}
