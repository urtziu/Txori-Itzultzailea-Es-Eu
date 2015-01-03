#!/usr/bin/perl -w
  
#use SOAP::Lite +trace => [debug => sub{print "<pre>"; print@_;print "</pre>\n"}];
use SOAP::Lite;
use MIME::Base64;



#irakurri parametroak
my $testua = $ARGV[0];


$encoded = MIME::Base64::encode_base64($testua);
$response =  SOAP::Lite
    -> proxy('http://ixa2.si.ehu.es/matxin_zerb/translate_named.cgi')
    -> uri('MATXIN')
    -> EsEu($encoded)
    -> result;
print MIME::Base64::decode_base64($response);

