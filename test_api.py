from time import sleep

import requests

CURRENCIES = [
	("AFAASDN", "Error"),
	("USD", "US Dollar"),
	("EUR", "Euro"),
	("CHF", "Swiss Franc"),
	("IQD", "Iraqi Dinar"),
	("JMD", "Jamaican Dollar"),
	("JOD", "Jordanian Dinar"),
	("KES", "Kenyan Shilling"),
	("PGK", "Kina"),
	("LAK", "Kip"),
	("KWD", "Kuwaiti Dinar"),
	("MWK", "Kwacha"),
	("AOA", "Kwanza"),
	("MMK", "Kyat"),
	("GEL", "Lari"),
	("LVL", "Latvian Lats"),
	("LBP", "Lebanese Pound"),
	("ALL", "Lek"),
	("AFN", "Afghani"),
	("DZD", "Algerian Dinar"),
	("ARS", "Argentine Peso"),
	("AMD", "Armenian Dram"),
	("AWG", "Aruban Guilder"),
	("AUD", "Australian Dollar"),
	("AZN", "Azerbaijanian Manat"),
	("BSD", "Bahamian Dollar"),
	("BHD", "Bahraini Dinar"),
	("THB", "Baht"),
	("PAB", "Balboa"),
	("BBD", "Barbados Dollar"),
	("BYR", "Belarussian Ruble"),
	("BZD", "Belize Dollar"),
	("BMD", "Bermudian Dollar"),
	("VEF", "Bolivar Fuerte"),
	("BOB", "Boliviano"),
	("BRL", "Brazilian Real"),
	("BND", "Brunei Dollar"),
	("BGN", "Bulgarian Lev"),
	("BIF", "Burundi Franc"),
	("CAD", "Canadian Dollar"),
	("CVE", "Cape Verde Escudo"),
	("KYD", "Cayman Islands Dollar"),
	("GHS", "Cedi"),
	("CLP", "Chilean Peso"),
	("COP", "Colombian Peso"),
	("KMF", "Comoro Franc"),
	("CDF", "Congolese Franc"),
	("BAM", "Convertible Mark"),
	("NIO", "Cordoba Oro"),
	("CRC", "Costa Rican Colon"),
	("HRK", "Croatian Kuna"),
	("CUP", "Cuban Peso"),
	("CZK", "Czech Koruna"),
	("GMD", "Dalasi"),
	("DKK", "Danish Krone"),
	("MKD", "Denar"),
	("DJF", "Djibouti Franc"),
	("STD", "Dobra"),
	("DOP", "Dominican Peso"),
	("VND", "Dong"),
	("XCD", "East Caribbean Dollar"),
	("EGP", "Egyptian Pound"),
	("SVC", "El Salvador Colon"),
	("ETB", "Ethiopian Birr"),

	("FKP", "Falkland Islands Pound"),
	("FJD", "Fiji Dollar"),
	("HUF", "Forint"),
	("GIP", "Gibraltar Pound"),
	("XAU", "Gold"),
	("HTG", "Gourde"),
	("PYG", "Guarani"),
	("GNF", "Guinea Franc"),
	("GYD", "Guyana Dollar"),
	("HKD", "Hong Kong Dollar"),
	("UAH", "Hryvnia"),
	("ISK", "Iceland Krona"),
	("INR", "Indian Rupee"),
	("IRR", "Iranian Rial"),

	("HNL", "Lempira"),
	("SLL", "Leone"),
	("RON", "Leu"),
	("LRD", "Liberian Dollar"),
	("LYD", "Libyan Dinar"),
	("SZL", "Lilangeni"),
	("LTL", "Lithuanian Litas"),
	("LSL", "Loti"),
	("MGA", "Malagasy Ariary"),
	("MYR", "Malaysian Ringgit"),
	("MUR", "Mauritius Rupee"),
	("MZN", "Metical"),
	("MXN", "Mexican Peso"),
	("MDL", "Moldovan Leu"),
	("MAD", "Moroccan Dirham"),
	("BOV", "Mvdol"),
	("NGN", "Naira"),
	("ERN", "Nakfa"),
	("NAD", "Namibia Dollar"),
	("NPR", "Nepalese Rupee"),
	("ANG", "Netherlands Antillean Guilder"),
	("ILS", "New Israeli Sheqel"),
	("TMT", "New Manat"),
	("TWD", "New Taiwan Dollar"),
	("NZD", "New Zealand Dollar"),
	("BTN", "Ngultrum"),
	("KPW", "North Korean Won"),
	("NOK", "Norwegian Krone"),
	("PEN", "Nuevo Sol"),
	("MRO", "Ouguiya"),
	("PKR", "Pakistan Rupee"),
	("XPD", "Palladium"),
	("MOP", "Pataca"),
	("TOP", "Pa???anga"),
	("CUC", "Peso Convertible"),
	("UYU", "Peso Uruguayo"),
	("PHP", "Philippine Peso"),
	("XPT", "Platinum"),
	("GBP", "Pound Sterling"),
	("BWP", "Pula"),
	("QAR", "Qatari Rial"),
	("GTQ", "Quetzal"),
	("ZAR", "Rand"),
	("OMR", "Rial Omani"),
	("KHR", "Riel"),
	("MVR", "Rufiyaa"),
	("IDR", "Rupiah"),
	("RUB", "Russian Ruble"),
	("RWF", "Rwanda Franc"),
	("SHP", "Saint Helena Pound"),
	("SAR", "Saudi Riyal"),
	("RSD", "Serbian Dinar"),
	("SCR", "Seychelles Rupee"),
	("XAG", "Silver"),
	("SGD", "Singapore Dollar"),
	("SBD", "Solomon Islands Dollar"),
	("KGS", "Som"),
	("SOS", "Somali Shilling"),
	("TJS", "Somoni"),
	("ZAR", "South African Rand"),
	("LKR", "Sri Lanka Rupee"),
	("XSU", "Sucre"),
	("SDG", "Sudanese Pound"),
	("SRD", "Surinam Dollar"),
	("SEK", "Swedish Krona"),

	("SYP", "Syrian Pound"),
	("BDT", "Taka"),
	("WST", "Tala"),
	("TZS", "Tanzanian Shilling"),
	("KZT", "Tenge"),
	("TTD", "Trinidad and Tobago Dollar"),
	("MNT", "Tugrik"),
	("TND", "Tunisian Dinar"),
	("TRY", "Turkish Lira"),
	("AED", "UAE Dirham"),

	("UGX", "Uganda Shilling"),
	("COU", "Unidad de Valor Real"),
	("CLF", "Unidades de fomento"),
	("UYI", "Uruguay Peso en Unidades Indexadas (URUIURUI)"),
	("UZS", "Uzbekistan Sum"),
	("VUV", "Vatu"),
	("KRW", "Won"),
	("YER", "Yemeni Rial"),
	("JPY", "Yen"),
	("CNY", "Yuan Renminbi"),
	("ZMK", "Zambian Kwacha"),
	("ZWL", "Zimbabwe Dollar"),
	("PLN", "Zloty")
]

met_broke = False
met_rich = False
met_error = False

i = 0
while i < len(CURRENCIES):
	r = requests.get(f'https://projects.alfabank.yuryborodin.ru/api/v1/currencies/{CURRENCIES[i][0]}')
	if r.json()['type'] == 'broke':
		met_broke = True
		print('#########################')
		print(f'MET BROKE STATUS  FOR {CURRENCIES[i][0]}: ', r.text)
		print('WAIT 10 SEC.')

	if r.json()['type'] == 'rich':
		met_rich = True
		print('#########################')
		print(f'MET RICH STATUS FOR {CURRENCIES[i][0]}: ', r.text)
		print('WAIT 10 SEC.')
	if r.json()['type'] == 'error':
		print('#########################')
		print(f'MET ERROR STATUS (1 ONE INTENTIONAL)  FOR {CURRENCIES[i][0]}: ', r.text)
		print('WAIT 10 SEC.')
		met_error = True
	if met_error and met_rich and met_broke:
		print('#########################')
		print('MET ALL POSSIBLE STATUSES. EXITING. ')
		break
	i += 1
	sleep(10)

