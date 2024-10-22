package backend.proyect_doctic_is1.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Categories")
public class CategoriesModel {

    @Id
    private String id;
    private String name;

    private List<SubCategory> subCategory;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SubCategory {
        private String idSubCategory;
        private String name;
    }
}
