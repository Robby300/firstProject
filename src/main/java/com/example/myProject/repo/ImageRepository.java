package com.example.myProject.repo;

import com.example.myProject.models.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, Long> {
}
