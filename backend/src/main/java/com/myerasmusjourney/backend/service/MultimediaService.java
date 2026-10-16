package com.myerasmusjourney.backend.service;

import com.myerasmusjourney.backend.domain.*;
import com.myerasmusjourney.backend.dto.ExperienceMultimediaSimpleDTO;
import com.myerasmusjourney.backend.dto.UserImageDTO;
import com.myerasmusjourney.backend.mapper.MultimediaMapper;
import com.myerasmusjourney.backend.repository.ExperienceMultimediaRepository;
import com.myerasmusjourney.backend.repository.UserImageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MultimediaService {

    @Autowired
    private ExperienceMultimediaRepository experienceMultimediaRepository;

    @Autowired
    private UserImageRepository userImageRepository;

    @Autowired
    private MultimediaMapper multimediaMapper;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private UserService userService;

    private boolean isActionNotAllowed(User user, Experience experience){
        return !experience.getAuthor().getId().equals(user.getId());
    }

    private boolean isActionNotAllowed(User user, Long id){
        return !user.getId().equals(id);
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

    @Transactional
    public UserImageDTO addUserImage(Long id, MultipartFile multipartFile) {
        User user = userService.getLoggedUser();
        if(isActionNotAllowed(user, id)) return new UserImageDTO(-1L, null, null);
        if(multipartFile == null) return null;
        try{
            if(!UserImage.contentTypeAccepted(multipartFile.getContentType())) return new UserImageDTO(0L, null, null);
            UserImage savedImage = user.getProfileImage();
            if(savedImage != null) user.setProfileImage(null);

            UserImage userImage = new UserImage(multipartFile, user);
            userImage = userImageRepository.save(userImage);
            userService.addUserImage(userImage, user);
            return multimediaMapper.toDTO(userImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Transactional
    public Resource loadFileAsResource(byte[] file) {
        return new ByteArrayResource(file);
    }

    public Multimedia getUserImageById(Long id) {
        return userImageRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Profile image doesn't exist"));
    }
}
