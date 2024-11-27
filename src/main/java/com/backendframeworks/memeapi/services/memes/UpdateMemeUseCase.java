package com.backendframeworks.memeapi.services.memes;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendframeworks.memeapi.dtos.memes.UpdateMemeDto;
import com.backendframeworks.memeapi.models.Meme;
import com.backendframeworks.memeapi.repositories.MemeRepository;

@Service
public class UpdateMemeUseCase {
    @Autowired
    private MemeRepository memeRepository;

    public Meme execute(UUID id, UpdateMemeDto dto) {
        Meme meme = memeRepository.findById(id).orElse(null);
        if (meme != null) {
            meme.setDescription(dto.description());
            return memeRepository.save(meme);
        }
        return null;
    }
}
