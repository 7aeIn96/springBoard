package org.project.board.models.file;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.project.board.entities.FileInfo;
import org.project.board.repositories.FileInfoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadService {
    @Value("${file.upload.path}")
    private String fileUploadPath;
    private final FileInfoRepository repository;
    private final HttpServletRequest request;

    /**
     *
     * @param files
     * @param gid - null 값이거나 "" 일때는 랜덤하게 하나 생성
     * @param location
     */
    public List<FileInfo> upload(MultipartFile[] files, String gid, String location) {
        gid = gid == null || gid.isBlank() ? UUID.randomUUID().toString() : gid;

        List<FileInfo> items = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            // 있을 때는 쪼개서 확장자로 가져오고 없을때는 Null
            String extension = fileName.lastIndexOf('.') != -1 ? fileName.substring(fileName.lastIndexOf('.') + 1) : null;

            FileInfo item = FileInfo.builder()
                    .gid(gid)
                    .location(location)
                    .fileName(fileName)
                    .extension(extension)
                    .contentType(file.getContentType())
                    .build();
            item = repository.saveAndFlush(item);

            items.add(item);
        }
        return items;
    }

    public void upload(MultipartFile[] files, String gid) {
        upload(files, gid, null);
    }

    public void upload(MultipartFile[] files) {
        upload(files, null);
    }
}
