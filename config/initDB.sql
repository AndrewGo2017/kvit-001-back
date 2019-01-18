drop table if exists setting;
drop table if exists main_field;

create table setting (
  id                  serial,

  name                text,

  inn                 text,

  kpp                 text,

  pay_acc             text,

  bank                text,

  bic                 text,

  cor_acc             text,

  bill_amount_on_page int,

  font_size           int,

  add_info            text,


  qr_add_info         text,

  type                text
);

create table main_field (
  id            serial,

  fio           text,

  adr           text,

  sum           text,

  contract      text,

  purpose       text,

  kbk           text,

  oktmo         text,


  fio_name      text,

  adr_name      text,

  sum_name      text,

  contract_name text,

  purpose_name  text,

  kbk_name      text,

  oktmo_name    text
);