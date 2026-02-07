package dev.anirudh.ecommerce.documents;

import dev.anirudh.ecommerce.constants.Constants;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.math.BigDecimal;

@Data
@Builder
@Document(indexName = Constants.product_Document)
@Setting(settingPath = "static/es-settings.json")
public class ElasticProduct {
    @Id
    @Field(type = FieldType.Keyword)
    private String id;
    @Field(type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Text)
    private String description;
    @Field(type = FieldType.Double)
    private Double availableQuantity;
    @Field(type = FieldType.Double)
    private BigDecimal price;
    @Field(type = FieldType.Object)
    private ElasticCategory category;

    @Builder
    @Data
    public static class ElasticCategory {
        @Field(type = FieldType.Integer)
        private Integer id;
        @Field(type = FieldType.Text)
        private String name;
        @Field(type = FieldType.Text)
        private String description;
    }

}
