# ES Sample
#### This is a simple project using [Elasticsearch](https://www.elastic.co/pt/what-is/elasticsearch)

## Useful links
- [Tutorial](https://tsh.io/blog/elasticsearch-tutorial/)
- [Getting Started](https://medium.com/tech-explained/getting-hands-on-with-elasticsearch-9969a2894f8a)
- [Mappings](https://logz.io/blog/elasticsearch-mapping/)
- [REST API](https://www.elastic.co/guide/en/elasticsearch/reference/current/rest-apis.html)
- [Java API](https://www.baeldung.com/elasticsearch-java)
- [Spring Data Elasticsearch](https://www.baeldung.com/spring-data-elasticsearch-tutorial)

## Index

It is a collection of data with similar characteristic.

##### Create an index
```
PUT /quotes
```

```
PUT /quotes_new
{
  "mappings" : {
    "properties": {
       "author" : { "type" : "keyword" },
       "content" : { "type" : "text" },
       "year" : { "type" : "integer" }
    }
  }
}
```

```
PUT /quotes
{
  "settings" : {
    "number_of_shards" : 3,
    "number_of_replicas" : 2
  },
  "mappings" : {
    "properties": {
       "author" : { "type" : "keyword" },
       "content" : { "type" : "text" },
       "year" : { "type" : "integer" }
    }
  }
}
```

##### Update an index
```
PUT /quotes/_settings
{
    "settings" : {
        "number_of_shards" : 3,
        "number_of_replicas" : 2
    }
}
```

##### List all indexes <sup>[1](https://www.elastic.co/guide/en/elasticsearch/reference/current/cat.html)</sup>
```
GET _cat/indices?v
```

##### Delete an index
```
DELETE /quotes
```

## Mappings

Defines how a document is indexed and how it indexes and stores its fields <sup>[2](https://www.elastic.co/guide/en/elasticsearch/reference/current/mapping-types.html)</sup>. It is similar to a database schema. It is not possible to alter a property type after the mapping is created, but it is possible to add new properties.

##### Create a mapping
```
PUT /quotes/_mapping
{
  "properties" : {
      "author" : { "type" : "text" },
      "content" : { "type" : "text" },
      "year" : { "type" : "integer" }
  }
}
```

## Reindex

Copies data from a index to another.

##### Reindex
```
POST /_reindex
{
  "source": {
    "index": "quotes"
  },
  "dest": {
    "index": "quotes_new"
  }
}
```

## Inserting data

##### Insert
```
POST /quotes/_doc
{
  "author": "Beverly Sills",
  "content": "You may be disappointed if you fail, but you are doomed if you don’t try.",
  "year": 1929
}
```

##### Insert with ID
```
POST /quotes/_doc/12345
{
  "author": "Test",
  "content": "Test Content.",
  "year": 2021
}
```

## Updating data

##### Update by ID
```
POST /quotes/_doc/12345
{
  "author" : "Farrah Gray",
  "content" : "Build your own dreams, or someone else will hire you to build theirs.",
  "year" : 1984,
  "country": "USA"
}
```

## Querying data

##### Query all data
```
GET /quotes/_search
{
    "query": {
        "match_all": { }
    }
}
```

##### Query by ID
```
GET /quotes/_doc/VcPEWngBNenQKory5G0i
```

##### Query containing word
```
GET /quotes/_search
{
  "query": {
    "match": {
      "content": "success"
    }
  }
}
```

##### Query using range and must clause
```
GET /quotes/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "range": {
            "year": {
              "lte": 1900
            }
          }
        },
        {
          "match": {
            "content": "success"
          }
        }
      ]
    }
  }, 
  "_source": "author"
}
```

##### Query using pagination
```
GET /quotes/_search
{
  "query": {
    "match_all": {}
  }, 
  "from": 0, 
  "size": 2
}
```

##### Query using full-text search
```
GET /quotes/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "match": {
            "content": "not to be a success"
          }
        }
      ]
    }
  }
}
```

##### Query using filter
```
GET /quotes/_search
{
  "query": {
    "bool": {
      "filter": [
        {
          "match": {
            "author": "Albert Einstein"
          }
        },
        {
          "range":{
            "year":{
              "gte":1800,
              "lte":1900
            }
          }
        }
      ]
    }
  }
}
```

##### Query using term
```
GET /quotes/_search
{
  "query" : {
    "term" : { "author" : "Beverly Sills" }
  }
}
```

##### Query using fuzzy search

The operator ~1 is used for fuzzy searching <sup>[3](https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-fuzzy-query.html)</sup>. Elasticsearch will try to find documents with misspelled word (Elastcsearch) that are one edit distance from it (one edit distance means replacing, adding, or deleting a character).

```
http://localhost:9200/quotes/_search?q=disppointed~1&pretty
```

```
GET /quotes/_search
{
  "query": {
    "fuzzy": {
      "content": {
        "value": "disppointed",
        "fuzziness": "AUTO",
        "max_expansions": 1,
        "prefix_length": 0,
        "transpositions": true,
        "rewrite": "constant_score"
      }
    }
  }
}
```

## Deleting data

##### Delete by ID
```
DELETE /quotes/_doc/VcPEWngBNenQKory5G0i
```