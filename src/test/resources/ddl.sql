DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS brand;


CREATE TABLE brand
(
  brand_id   VARCHAR(13)  NOT NULL,
  brand_name VARCHAR(70)  NOT NULL,
  created_at TIMESTAMP    NOT NULL,
  created_by VARCHAR(100) NOT NULL,
  updated_at TIMESTAMP    NOT NULL,
  updated_by VARCHAR(100) NOT NULL,
  CONSTRAINT pk_brand PRIMARY KEY (brand_id)
);

COMMENT
ON TABLE brand IS '브랜드 정보 테이블';
COMMENT
ON COLUMN brand.brand_id IS '브랜드 정보 ID (PK, TSID)';
COMMENT
ON COLUMN brand.brand_name IS '브랜드 이름';
COMMENT
ON COLUMN brand.created_at IS '최초 생성시각';
COMMENT
ON COLUMN brand.created_by IS '최초 생성자';
COMMENT
ON COLUMN brand.updated_at IS '최종 수정시각';
COMMENT
ON COLUMN brand.updated_by IS '최종 수정자';

CREATE TABLE category
(
  category_id   VARCHAR(13)  NOT NULL,
  category_name VARCHAR(70)  NOT NULL,
  created_at    TIMESTAMP    NOT NULL,
  created_by    VARCHAR(100) NOT NULL,
  updated_at    TIMESTAMP    NOT NULL,
  updated_by    VARCHAR(100) NOT NULL,
  CONSTRAINT pk_category PRIMARY KEY (category_id)
);

COMMENT
ON TABLE category IS '키테고리 정보 테이블';
COMMENT
ON COLUMN category.category_id IS '카테고리 정보 ID (PK, TSID)';
COMMENT
ON COLUMN category.category_name IS '카테고리 이름';
COMMENT
ON COLUMN category.created_at IS '최초 생성시각';
COMMENT
ON COLUMN category.created_by IS '최초 생성자';
COMMENT
ON COLUMN category.updated_at IS '최종 수정시각';
COMMENT
ON COLUMN category.updated_by IS '최종 수정자';

CREATE TABLE product
(
  product_id    VARCHAR(13)  NOT NULL,
  brand_id      VARCHAR(13)  NOT NULL,
  category_id   VARCHAR(13)  NOT NULL,
  product_name  VARCHAR(70)  NOT NULL,
  product_price BIGINT       NOT NULL,
  created_at    TIMESTAMP    NOT NULL,
  created_by    VARCHAR(100) NOT NULL,
  updated_at    TIMESTAMP    NOT NULL,
  updated_by    VARCHAR(100) NOT NULL,

  CONSTRAINT pk_product PRIMARY KEY (product_id),
  CONSTRAINT fk_product_on_brand FOREIGN KEY (brand_id) REFERENCES brand (brand_id),
  CONSTRAINT fk_product_on_category FOREIGN KEY (category_id) REFERENCES category (category_id)
);

COMMENT
ON TABLE product IS '키테고리 정보 테이블';
COMMENT
ON COLUMN product.product_id IS '제품 정보 ID (PK, TSID)';
COMMENT
ON COLUMN product.brand_id IS '브랜드 정보 ID (PK, TSID)';
COMMENT
ON COLUMN product.category_id IS '카테고리 정보 ID (FK, TSID)';
COMMENT
ON COLUMN product.product_name IS '제품 이름';
COMMENT
ON COLUMN product.created_at IS '최초 생성시각';
COMMENT
ON COLUMN product.created_by IS '최초 생성자';
COMMENT
ON COLUMN product.updated_at IS '최종 수정시각';
COMMENT
ON COLUMN product.updated_by IS '최종 수정자';

CREATE INDEX idx_product_on_category_and_price ON product (category_id, product_price);
CREATE INDEX idx_product_on_brand_and_category_and_price ON product (brand_id, category_id, product_price);
