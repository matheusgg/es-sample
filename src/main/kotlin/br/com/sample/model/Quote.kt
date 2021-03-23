package br.com.sample.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType.Integer
import org.springframework.data.elasticsearch.annotations.FieldType.Keyword
import org.springframework.data.elasticsearch.annotations.FieldType.Text

@Document(indexName = "quotes")
data class Quote(
    @JsonProperty(access = READ_ONLY) @Id var id: String? = null,
    @Field(type = Keyword) var author: String,
    @Field(type = Text) var content: String,
    @Field(type = Integer) var year: Int
)
