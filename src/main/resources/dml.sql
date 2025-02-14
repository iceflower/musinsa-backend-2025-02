-- PK 값용 TSID 데이터는 아래 사이트를 활용하여 생성하였습니다.
-- https://www.tsidgenerator.com/

-- 브랜드 데이터
INSERT INTO brand (brand_id, brand_name, created_at, created_by, updated_at, updated_by) VALUES ('0JS975W4891XQ', 'A', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO brand (brand_id, brand_name, created_at, created_by, updated_at, updated_by) VALUES ('0JS976HPR93QN', 'B', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO brand (brand_id, brand_name, created_at, created_by, updated_at, updated_by) VALUES ('0JS97784W93T7', 'C', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO brand (brand_id, brand_name, created_at, created_by, updated_at, updated_by) VALUES ('0JS977T4C92W1', 'D', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO brand (brand_id, brand_name, created_at, created_by, updated_at, updated_by) VALUES ('0JS978N4092X8', 'E', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO brand (brand_id, brand_name, created_at, created_by, updated_at, updated_by) VALUES ('0JS9797T091BX', 'F', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO brand (brand_id, brand_name, created_at, created_by, updated_at, updated_by) VALUES ('0JS979RGC9352', 'G', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO brand (brand_id, brand_name, created_at, created_by, updated_at, updated_by) VALUES ('0JS97AA8093SH', 'H', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO brand (brand_id, brand_name, created_at, created_by, updated_at, updated_by) VALUES ('0JS97ATSM91HD', 'I', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');

-- 카테고리 데이터
INSERT INTO category (category_id, category_name, created_at, created_by, updated_at, updated_by) VALUES ('0JS97BHFR9353', '상의', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO category (category_id, category_name, created_at, created_by, updated_at, updated_by) VALUES ('0JS97C63491AD', '아우터', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO category (category_id, category_name, created_at, created_by, updated_at, updated_by) VALUES ('0JS97CN409252', '바지', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO category (category_id, category_name, created_at, created_by, updated_at, updated_by) VALUES ('0JS97D4JR91GM', '스니커즈', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO category (category_id, category_name, created_at, created_by, updated_at, updated_by) VALUES ('0JS97DPTG92VE', '가방', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO category (category_id, category_name, created_at, created_by, updated_at, updated_by) VALUES ('0JS97EDJG9333', '모자', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO category (category_id, category_name, created_at, created_by, updated_at, updated_by) VALUES ('0JS97EYY8937F', '양말', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO category (category_id, category_name, created_at, created_by, updated_at, updated_by) VALUES ('0JS97FHFM91FG', '악세서리', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');

-- A 브랜드 제품
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VDTS492Z8', '0JS975W4891XQ', '0JS97BHFR9353','[A] 상의', 11200, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VEKVM92K1', '0JS975W4891XQ', '0JS97C63491AD','[A] 아우터', 5500, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VFF10909S', '0JS975W4891XQ', '0JS97CN409252','[A] 바지', 4200, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VG49M936Z', '0JS975W4891XQ', '0JS97D4JR91GM','[A] 스니커즈', 9000, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VGV3892J0', '0JS975W4891XQ', '0JS97DPTG92VE','[A] 가방', 2000, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VH9T891HE', '0JS975W4891XQ', '0JS97EDJG9333','[A] 모자', 1700, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VHXKR91WD', '0JS975W4891XQ', '0JS97EYY8937F','[A] 양말', 1800, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VJHWW91E4', '0JS975W4891XQ', '0JS97FHFM91FG','[A] 악세서리', 2300, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');

-- B 브랜드 제품
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VK2P093NP', '0JS976HPR93QN', '0JS97BHFR9353','[B] 상의', 10500, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VM1TM93D0', '0JS976HPR93QN', '0JS97C63491AD','[B] 아우터', 5900, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VMMZ491SC', '0JS976HPR93QN', '0JS97CN409252','[B] 바지', 3800, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VN3N493J8', '0JS976HPR93QN', '0JS97D4JR91GM','[B] 스니커즈', 9100, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VNMZC91JB', '0JS976HPR93QN', '0JS97DPTG92VE','[B] 가방', 2100, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VP70091S6', '0JS976HPR93QN', '0JS97EDJG9333','[B] 모자', 2000, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VPW4G92JD', '0JS976HPR93QN', '0JS97EYY8937F','[B] 양말', 2000, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VQDHG93B7', '0JS976HPR93QN', '0JS97FHFM91FG','[B] 악세서리', 2200, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');

-- C 브랜드 제품
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VQYH891QT', '0JS97784W93T7', '0JS97BHFR9353','[C] 상의', 10000, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VRVFR90PB', '0JS97784W93T7', '0JS97C63491AD','[C] 아우터', 6200, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VSC7R93N0', '0JS97784W93T7', '0JS97CN409252','[C] 바지', 3300, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VT45R90PW', '0JS97784W93T7', '0JS97D4JR91GM','[C] 스니커즈', 9200, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VTNFG915T', '0JS97784W93T7', '0JS97DPTG92VE','[C] 가방', 2200, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VV9A0923Z', '0JS97784W93T7', '0JS97EDJG9333','[C] 모자', 1900, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VVS24910C', '0JS97784W93T7', '0JS97EYY8937F','[C] 양말', 2200, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VW71G90HM', '0JS97784W93T7', '0JS97FHFM91FG','[C] 악세서리', 2100, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');

-- D 브랜드 제품
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VWPJC93NY', '0JS977T4C92W1', '0JS97BHFR9353','[D] 상의', 10100, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VX7ZC91JN', '0JS977T4C92W1', '0JS97C63491AD','[D] 아우터', 5100, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VXRGM91PW', '0JS977T4C92W1', '0JS97CN409252','[D] 바지', 3000, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VYEN0906N', '0JS977T4C92W1', '0JS97D4JR91GM','[D] 스니커즈', 9500, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VZHC0924D', '0JS977T4C92W1', '0JS97DPTG92VE','[D] 가방', 2500, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9VZZ9M90R0', '0JS977T4C92W1', '0JS97EDJG9333','[D] 모자', 1500, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9W0KPG92W0', '0JS977T4C92W1', '0JS97EYY8937F','[D] 양말', 2400, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9W18KC9349', '0JS977T4C92W1', '0JS97FHFM91FG','[D] 악세서리', 2000, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');

-- E 브랜드 제품
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9W1WTW90K8', '0JS978N4092X8', '0JS97BHFR9353','[E] 상의', 10700, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9W2BM0922Y', '0JS978N4092X8', '0JS97C63491AD','[E] 아우터', 5000, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9W2XM490ES', '0JS978N4092X8', '0JS97CN409252','[E] 바지', 3800, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9W3E4W92MX', '0JS978N4092X8', '0JS97D4JR91GM','[E] 스니커즈', 9900, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9W40W093GV', '0JS978N4092X8', '0JS97DPTG92VE','[E] 가방', 2300, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9W4H8C934W', '0JS978N4092X8', '0JS97EDJG9333','[E] 모자', 1800, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9W57MW9155', '0JS978N4092X8', '0JS97EYY8937F','[E] 양말', 2100, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9W5S2C9123', '0JS978N4092X8', '0JS97FHFM91FG','[E] 악세서리', 2100, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');

-- F 브랜드 제품
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9W6MGM92SX', '0JS9797T091BX', '0JS97BHFR9353','[F] 상의', 11200, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9W76ZW9141', '0JS9797T091BX', '0JS97C63491AD','[F] 아우터', 7200, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9W7QXC91JZ', '0JS9797T091BX', '0JS97CN409252','[F] 바지', 4000, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9W891492B5', '0JS9797T091BX', '0JS97D4JR91GM','[F] 스니커즈', 9300, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9W8S5R93BT', '0JS9797T091BX', '0JS97DPTG92VE','[F] 가방', 2100, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9W99W492F7', '0JS9797T091BX', '0JS97EDJG9333','[F] 모자', 1600, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9W9WQ8900R', '0JS9797T091BX', '0JS97EYY8937F','[F] 양말', 2300, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9WAE5891WV', '0JS9797T091BX', '0JS97FHFM91FG','[F] 악세서리', 1900, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');

-- G 브랜드 제품
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9WB7JR922G', '0JS979RGC9352', '0JS97BHFR9353','[G] 상의', 10500, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9WBW90927H', '0JS979RGC9352', '0JS97C63491AD','[G] 아우터', 5800, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9WCDPM937G', '0JS979RGC9352', '0JS97CN409252','[G] 바지', 3900, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9WCZN891ZP', '0JS979RGC9352', '0JS97D4JR91GM','[G] 스니커즈', 9000, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9WDFP490ZD', '0JS979RGC9352', '0JS97DPTG92VE','[G] 가방', 2200, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9WDZMM9041', '0JS979RGC9352', '0JS97EDJG9333','[G] 모자', 1700, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9WEF8490PG', '0JS979RGC9352', '0JS97EYY8937F','[G] 양말', 2100, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9WEZQ093CE', '0JS979RGC9352', '0JS97FHFM91FG','[G] 악세서리', 2000, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');

-- H 브랜드 제품
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9WFKT4920Z', '0JS97AA8093SH', '0JS97BHFR9353','[H] 상의', 10800, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9WG36C90J6', '0JS97AA8093SH', '0JS97C63491AD','[H] 아우터', 6300, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9WGHV890CG', '0JS97AA8093SH', '0JS97CN409252','[H] 바지', 3100, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9WH8C490N7', '0JS97AA8093SH', '0JS97D4JR91GM','[H] 스니커즈', 9700, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9WHR88938F', '0JS97AA8093SH', '0JS97DPTG92VE','[H] 가방', 2100, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9WJ99093P3', '0JS97AA8093SH', '0JS97EDJG9333','[H] 모자', 1600, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9WJWCC921E', '0JS97AA8093SH', '0JS97EYY8937F','[H] 양말', 2000, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9WKDDM908N', '0JS97AA8093SH', '0JS97FHFM91FG','[H] 악세서리', 2000, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');

-- I 브랜드 제품
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9WKYAR925B', '0JS97ATSM91HD', '0JS97BHFR9353','[I] 상의', 11400, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9WMFC493ZH', '0JS97ATSM91HD', '0JS97C63491AD','[I] 아우터', 6700, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9WN00R93T6', '0JS97ATSM91HD', '0JS97CN409252','[I] 바지', 3200, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9WNNYC93Z8', '0JS97ATSM91HD', '0JS97D4JR91GM','[I] 스니커즈', 9500, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9WP8HG9039', '0JS97ATSM91HD', '0JS97DPTG92VE','[I] 가방', 2400, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9WPRGM91GA', '0JS97ATSM91HD', '0JS97EDJG9333','[I] 모자', 1700, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9WQ88W93YX', '0JS97ATSM91HD', '0JS97EYY8937F','[I] 양말', 1700, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
INSERT INTO product (product_id, brand_id, category_id, product_name, product_price, created_at, created_by, updated_at, updated_by) VALUES ('0JS9WQS2G92XH', '0JS97ATSM91HD', '0JS97FHFM91FG','[I] 악세서리', 2400, CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM');
