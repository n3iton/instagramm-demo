package com.example.demo.repository;

import com.example.demo.entity.ImageModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageModel, Long> {

  Optional<ImageModel> findByUserId(Long userId);

  Optional<ImageModel> findByPostId(Long postId);
}
