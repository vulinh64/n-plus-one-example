package com.vulinh.data.mapper;

import com.vulinh.data.dto.AuthorDTO;
import com.vulinh.data.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {

  AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

  AuthorDTO toAuthor(Author authorList);
}
