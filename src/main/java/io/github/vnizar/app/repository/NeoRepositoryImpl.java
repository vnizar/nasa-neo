package io.github.vnizar.app.repository;

import io.github.vnizar.app.dto.FeedResponseDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NeoRepositoryImpl implements NeoRepository {


    @Override
    public List<FeedResponseDto> getFeed() {
        return null;
    }
}
