package com.example.myProject.repo;

import com.example.myProject.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long>{
}
