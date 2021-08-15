import socket, threading;
import pymysql

# DB 연걸
local = 'dbtest.cuslvraxrcdc.ap-northeast-2.rds.amazonaws.com'
db = pymysql.connect(
    host=local,
    user='user',
    db='ttockshow',
    password='qwert123',
    charset='utf8'
)
curs = db.cursor()
sql = """select * from review"""
curs.execute(sql)
select =list(curs.fetchall())
db.commit()
sendD=[]
for i in range(len(select)):
    for j in range(1,len(select[i])):
        sendD.append(str(select[i][j]))
sendD = "#".join(sendD)

def binder(client_socket, addr):
    print('Connected by', addr);
    try:
        while True:

            msg = sendD
            data = msg.encode();

            data1 = "아이스브레이커스"
            data1 = data1.encode();
            length1 = len(data1);

            length = len(data);
            client_socket.sendall(length.to_bytes(4, byteorder='little'));
            client_socket.sendall(data);

    except:
        print("except : " , addr);
    finally:
        client_socket.close();
server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM);
server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1);
server_socket.bind(('', 9999));
server_socket.listen();
try:
    print("Start");
    while True:
        client_socket, addr = server_socket.accept();
        th = threading.Thread(target=binder, args = (client_socket,addr));
        th.start();
except:
    print("server");
finally:
    server_socket.close();


