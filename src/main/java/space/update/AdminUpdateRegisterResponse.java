package space.update;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminUpdateRegisterResponse {

    private Long id;

    public static AdminUpdateRegisterResponse from(UpdateNotice notice){
        return new AdminUpdateRegisterResponse(notice.getId());
    }
}
