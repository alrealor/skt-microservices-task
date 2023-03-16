#####################
SKT-MICROSERVICE-TASK
#####################


# postgresql database assets
----------------------------

    // sequence for column id of product table
    CREATE SEQUENCE product_sequence
    START WITH 1000
    INCREMENT BY 1
    MINVALUE 1000;

    // product table
    CREATE TABLE IF NOT EXISTS public.product
    (
        id integer NOT NULL,
        name text COLLATE pg_catalog."default" NOT NULL,
        price numeric NOT NULL,
        CONSTRAINT product_pkey PRIMARY KEY (id)
    );

    // alter product table to set sequence to id column
    ALTER TABLE public.product
    ALTER COLUMN id SET DEFAULT nextval('product_sequence');

    // stored function to insert a new product into product table
    CREATE OR REPLACE FUNCTION public.insert_product(p_name text, p_price numeric)
    RETURNS numeric
    LANGUAGE SQL
    AS $BODY$
        INSERT INTO public.product (name, price)
        VALUES(p_name, p_price)
        RETURNING id;
    $BODY$;

    // stored function to get all available products from product table
    CREATE OR REPLACE FUNCTION get_products()
        RETURNS SETOF product
    LANGUAGE SQL
    AS $BODY$
        SELECT * FROM product;
    $BODY$;