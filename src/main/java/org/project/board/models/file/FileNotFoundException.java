package org.project.board.models.file;

import org.project.board.commons.CommonException;
import org.springframework.http.HttpStatus;

public class FileNotFoundException extends CommonException {
    public FileNotFoundException() {
        super(bundleValidation.getString("File.notExists"), HttpStatus.BAD_REQUEST);
    }
}