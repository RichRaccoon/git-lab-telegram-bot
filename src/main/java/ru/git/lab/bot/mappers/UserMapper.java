package ru.git.lab.bot.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.git.lab.bot.api.mr.User;
import ru.git.lab.bot.model.entities.GitUserEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends AbstractMapper<GitUserEntity, User> {

    @Override
    @Mapping(source = "gitId", target = "id")
    User toDto(GitUserEntity gitUserEntity);

    @Override
    @Mapping(source = "id", target = "gitId")
    @Mapping(target = "id", ignore = true)
    GitUserEntity toEntity(User user);
}
