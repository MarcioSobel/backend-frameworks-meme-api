package com.backendframeworks.memeapi.services.memes;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendframeworks.memeapi.models.Meme;
import com.backendframeworks.memeapi.repositories.MemeRepository;

@Service
public class GetMemeByIdUseCase {

    @Autowired
    private MemeRepository memeRepository;

    public Meme execute(UUID id) {
        return memeRepository.findById(id).orElse(null);
    }
}
