package com.backendframeworks.memeapi.services.memes;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendframeworks.memeapi.dtos.memes.CreateMemeDto;
import com.backendframeworks.memeapi.exceptions.pages.PageNotFound;
import com.backendframeworks.memeapi.models.Meme;
import com.backendframeworks.memeapi.models.Page;
import com.backendframeworks.memeapi.repositories.MemeRepository;
import com.backendframeworks.memeapi.repositories.PageRepository;

@Service
public class CreateMemeUseCase {

    @Autowired
    private MemeRepository memeRepository;

    @Autowired
    private PageRepository pageRepository;

    public Meme execute(CreateMemeDto dto) {
        Meme meme = new Meme();
        BeanUtils.copyProperties(dto, meme);

        Optional<Page> page = pageRepository.findById(dto.pageId());
        if (page.isEmpty()) {
            throw new PageNotFound();
        }

        meme.setPage(page.get());
        return memeRepository.save(meme);
    }
}
