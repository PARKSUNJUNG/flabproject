package space.update;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class UpdateFile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    private UpdateNotice notice;

    void setNotice(UpdateNotice notice){
        this.notice = notice;
    }

    private String originalName;
    private String storedName;
    private String filePath;

    public UpdateFile(String originalName, String storedName, String filePath) {
        this.originalName = originalName;
        this.storedName = storedName;
        this.filePath = filePath;
    }
}
