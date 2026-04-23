package com.session13.ex05.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.hibernate.LazyInitializationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Xử lý LazyInitializationException
     * Lỗi này xảy ra khi cố gắng truy cập vào dữ liệu lazy-loaded sau khi Session đã đóng
     */
    @ExceptionHandler(LazyInitializationException.class)
    public String handleLazyInitializationException(LazyInitializationException ex, Model model) {
        model.addAttribute("errorMessage",
            "Lỗi tải dữ liệu: Dữ liệu liên quan chưa được tải. Vui lòng thử lại!");
        return "error";
    }

    /**
     * Xử lý IllegalArgumentException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    /**
     * Xử lý 404 Not Found
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFound(NoHandlerFoundException ex, Model model) {
        model.addAttribute("errorMessage", "Trang không tồn tại (404)");
        return "error";
    }

    /**
     * Xử lý các lỗi chung
     */
    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {
        ex.printStackTrace();
        model.addAttribute("errorMessage", "Lỗi hệ thống: " + ex.getMessage());
        return "error";
    }
}

