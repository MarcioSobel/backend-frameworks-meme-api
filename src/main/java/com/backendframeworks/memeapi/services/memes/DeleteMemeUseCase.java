package com.backendframeworks.memeapi.services.memes;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.backendframeworks.memeapi.repositories.MemeRepository;

@Service
public class DeleteMemeUseCase {

    @Autowired
    private MemeRepository memeRepository;

    public void execute(UUID id) {
        memeRepository.deleteById(id);
    }
}
