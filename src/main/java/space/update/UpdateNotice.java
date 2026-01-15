package space.update;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "update_notice")
@Getter
@NoArgsConstructor
public class UpdateNotice {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UpdateCategory category;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UpdateFile> files = new ArrayList<>();

    public void addFile(UpdateFile file){
        files.add(file);
        file.setNotice(this);
    }

    public UpdateNotice(String title, UpdateCategory category, String content) {
        this.title = title;
        this.category = category;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    public void update(String title, UpdateCategory category, String content){
        this.title = title;
        this.category = category;
        this.content = content;
    }
}
