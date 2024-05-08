package kr.co.ordermanagement.presentation.dto;

public class ErrorMessageDto {
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public ErrorMessageDto(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
