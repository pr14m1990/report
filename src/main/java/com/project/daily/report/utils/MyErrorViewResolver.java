package com.project.daily.report.utils;

import com.google.common.base.Throwables;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Configuration
public class MyErrorViewResolver implements ErrorViewResolver {

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status,
                                         Map<String, Object> model) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        String exceptionMessage = getExceptionMessage(throwable, statusCode);

        ModelAndView modelv = new ModelAndView("error/error");
        modelv.addObject("status", request.getAttribute("javax.servlet.error.status_code"));
        modelv.addObject("message", exceptionMessage);
        return modelv;
    }

    private String getExceptionMessage(Throwable throwable, Integer statusCode) {
        if (throwable != null) {
            return Throwables.getRootCause(throwable).getMessage();
        }

        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
        return httpStatus.getReasonPhrase();
    }
}
