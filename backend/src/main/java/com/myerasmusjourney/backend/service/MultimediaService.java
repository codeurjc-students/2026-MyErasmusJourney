package com.myerasmusjourney.backend.service;

import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.domain.ExperienceMultimedia;
import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.ExperienceMultimediaSimpleDTO;
import com.myerasmusjourney.backend.mapper.MultimediaMapper;
import com.myerasmusjourney.backend.repository.ExperienceMultimediaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
public class MultimediaService {

    @Autowired
    private ExperienceMultimediaRepository experienceMultimediaRepository;

    @Autowired
    private MultimediaMapper multimediaMapper;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private UserService userService;

    private boolean isActionNotAllowed(User user, Experience experience){
        return !experience.getAuthor().getId().equals(user.getId());
    }

    @Transactional
    public List<ExperienceMultimediaSimpleDTO> addMultimedia (Long experienceId, List<MultipartFile> multimediaList) {
            Experience experience = experienceService.getExperience(experienceId);
        User user = userService.getLoggedUser();

        if(isActionNotAllowed(user, experience)) return null;
        if (multimediaList == null) return new LinkedList<>();

        List<ExperienceMultimedia> experienceMultimediaList = new LinkedList<>();
        for(MultipartFile media: multimediaList){
            try {
                if(!ExperienceMultimedia.contentTypeAccepted(media.getContentType())) return List.of();
                ExperienceMultimedia experienceMultimedia = new ExperienceMultimedia(media, experience);
                experienceMultimedia = experienceMultimediaRepository.save(experienceMultimedia);
                experienceService.addMultimedia(experienceId, experienceMultimedia);
                experienceMultimediaList.add(experienceMultimedia);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return multimediaMapper.toSimpleDTOs(experienceMultimediaList);
    }
}
