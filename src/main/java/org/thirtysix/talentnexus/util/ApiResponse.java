package org.thirtysix.talentnexus.util;

import lombok.Getter;
import lombok.Setter;
import org.thirtysix.talentnexus.pojo.JobSeeker;
import org.thirtysix.talentnexus.pojo.Resume;

/**
 * 接口返回值
 * @param <T> 数据泛型
 */
@Setter
@Getter
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "Success", data);
    }

    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }

    public static void fillResume(Resume resume, JobSeeker jobSeeker) {
        resume.setFullName(jobSeeker.getFullName());
        resume.setEmail(jobSeeker.getEmail());
        resume.setBirthDate(jobSeeker.getBirthDate());
        resume.setPhone(jobSeeker.getPhone());
        resume.setGender(jobSeeker.getGender());
        resume.setAddress(jobSeeker.getAddress());
        resume.setUniversity(jobSeeker.getUniversity());
        resume.setMajor(jobSeeker.getMajor());
        resume.setDegree(jobSeeker.getDegree());
    }
}

