def match(a, b):
return a == b
def fuzz_match(a, b):
a_items = a.split(',')
b_items = b.split(',')
for i in range(3):
if a_items[i] != b_items[i]:
return False
return True
 
def offset_match(a, b):
a_items = a.split(',')
b_items = b.split(',')
for i in (0,2):
if a_items[i] != b_items[i]:
return False
return a_items[1] != b_items[1]
 
def in_list_helper(trades, offset_match):
buys, sells = [], []
for trade in trades:
if trade[5] == 'B':
buys.append(trade)
else:
sells.append(trade)
um_buys, um_sells = helper(buys, sells, offset_match)
return um_buys + um_sells
 
def helper(house_trades, street_trades, func):
house_trades = sorted(house_trades)[::-1]
street_trades = sorted(street_trades)[::-1]
um_house, um_street = [], []
while house_trades and street_trades:
curr_house = house_trades[-1]
curr_street = street_trades[-1]
if func(curr_house, curr_street):
house_trades.pop()
street_trades.pop()
elif curr_house < curr_street:
house_trades.pop()
um_house.append(curr_house)
else:
street_trades.pop()
um_street.append(curr_street)
return um_house + house_trades, um_street + street_trades
 
def compare_trades(house_trades, street_trades):
house_res, street_res = helper(house_trades, street_trades, match)
house_res2, street_res2 = helper(house_res, street_res, fuzz_match)
house_res3, street_res3 = in_list_helper(house_res2, offset_match), in_list_helper(street_res2, offset_match)
return sorted(house_res3 + street_res3)
