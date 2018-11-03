package com.protocol7.nettyquick.tls;

import com.protocol7.nettyquick.utils.Hex;
import org.junit.Test;

import javax.crypto.AEADBadTagException;
import java.security.GeneralSecurityException;

import static org.junit.Assert.assertArrayEquals;

public class AEADTest {

    private final byte[] otherKey = Hex.dehex("030ebb472d95c53d46d2921ba4cbf4d7");
    private final byte[] myKey    = Hex.dehex("be0de0e5bbad5ed67f486f387454220f");
    private final byte[] otherIV  = Hex.dehex("df060a12c035c37b2c568e19");
    private final byte[] myIV     = Hex.dehex("1fd3e0b3df9cbff12eb94b62");

    private final String aadStr = "ff00000065f0c6377cacd2f8dfc3db23154d303f8f16b4d00044938001";

    private final byte[] aad = Hex.dehex(aadStr);
    private final int packetNumber = 1;

    private byte[] plainText = Hex.dehex("1800414b010001470303a76cc637036e871b63c463a73175ca81a5f09b14e80f58715d52c8f5e90a794a10fe8a3d447a7ea799dfd5bdbffca6e5bc0026130113021303c02fc030c02bc02ccca8cca9c013c009c014c00a009c009d002f0035c012000a010000e8000000150013000010717569632e636c656d656e74652e696f000500050100000000000a000a0008001d001700180019000b00020100000d001800160804040104030805050105030806060106030201020300320012001004010403050105030601060302010203ff0100010000120000003300260024001d0020a62c058352d7a007efbf4b944fad2dbcdf80b6ab56504533fc04a360a06dcc20002b00030203040ff5004200000000003c0000000400008000000a000400008000000b000400008000000100040000c00000020002006400080002006400030002001e0005000205ac0009000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");

    private byte[] myCipherText = Hex.dehex(aadStr + "739de88ba21fc7a3c7fbbfd872a5b2465cb6021810b3aede60b3a92964e4b1a86a9cfcd81672a1b0a83715187c05665c3ad9eaa9ac592a970cb23fa6efff19158769bb9f983656fc383149e57e0c9433af521dd860cc8fe710e4499fba52b96140f7e66c208e6a846b53cec016a66a5e812c277e01ad551fec0f6793634c06b2763c993d210a26a0c8a08e75e6ed49898e244214f2af8d70876f640ed53200c6b43f29a24eaf5c8833b64f837a2c8164a57f8fcb10a1297881a1bd72e45aae416aebae98685bfacd3b8e769da52ac697a11fe75224062ca1ac5d0a60400ef31521a5b737779a4ded4d178734ad4f05926b35897eaba41c1f0d623e4fc3cddf02a08154e38b342c2e85a2e1f7d4fc0c51deb9095023cc54860efee0bebc35690a10f18575d90215315080f661433873fdfadec73e72e0fcc28243022436f20a9be67dec62fecefc842fde36a26e1aab187830f2ea6f0a53c06f734446b4c290927cac60b805cccdfae9769ea19f196c16b8f84ac9dd2dc47783836f1cf734d2a8c739ba840ebb51471f9e3ce63f4a4aed913931d09b0b4115508c6b7c29c70f58e80da5b5fa8b02eef1491d49774ca31f43de366711698567927bcf6f8ed8c577c73d83752710d7d5c1880856ff80411a26193ef2442ae997c28563f4e320a6b1e5e5fc54d225d6b15e6a1cd1b3cba633b39d37bdd9606449610040b7bfee2c8585b8e43d897cedf238a6b97288c152b650ae57954b8200ffa9aa39e0a92492b5240bf48fa84b3095df2cb8139dcc6343a8f990ff417af8dba5cce371b3164bebf8f0baa0e8f8b1f8e7ec9bf960607bcbc28e9d31c888ce49684c3706c270c5090bc496d6a89b2e1ff52ce62c5e11233670f41f5a2e77f646da00e61c9b45a24fd8b6d15b08587f0d9bc60e60e5026318b9a3d5df2e5f62b3565d03d01b7d702ec2a433f239fe903690f7ef2ca90a445b4a4f6b47f493ed4a228790b28e0594d3bb2bdea8aeda10a3d1e9cefc107cc898a8f4d32ff4b58f4d69ab52c44b320893e1cf4e85fa58699c77a0573fd6255cb2164e8b68b21049a2a5fc763d96116091a209a8bb56d99e629464e2627563c1d657940498715fd566f858a019d2d2076006f47274457375d39c54f1b68e237658065d3bad39deeee4967570e2ddc67afcf1371bf8148063ce647c244d3ecc6a778716998305bae2f9d7fb5c5908f3577306b22cec49be7c49cdb79733440b93cc8632b8062678931d23c4462a397194968015ac7fd0bf55569447def88bfddf9e545132637afce203f27cc5f38851c080dd0f146aad70be3c33d684328afaae2f811c98c4f12274773be9c6690476339ae20ec335ded539c59d51593e4ebd0a244abda1c1504a8ba86120bbe299e0cf3bc64e11c9a068c8ea1488d624ec641af61bbf70d9e21d350839ede20be7183ab8115cc965572074632ed517de68a5eb98fbd6032ed9a1902ede3a4e8aa89c6380ff19571ee6123dae53ad71f7946b181b5312b72010391b0a180480b0fbb8c190f3b6508e09c5b345c7d8829471a6f7926f0db15f7c893cd2c688803c63cf6faa61d0e48ac0930192832bf647aa2fd6500a5d383bea35e37f2e77f36d7e1cebddb21482be79438a1b7f9a6908c322ba548e1374");

    private byte[] otherCipherText = Hex.dehex("6044ad0284d98339a338cccda2357e9d55f04556c8b3b879508cdbb047484267b5aad7a2e8ac26151da93b9589cb98f1d397b4acd5f92a1dbe2885bb995016114e375bf17e75c2173cb20d128309811969b08c52de1dbd877c852087b2751b5c3a5165cd76c93e2cf600b167d76f469744832d474ff48eaa4860e67134151bd91105965b99e1e77a915c2deed5d88426b71fde9b752fd91b7b5aa1474f72cc16530c622d3d0183e965a96cd0014dc5178ac1beb46a60a80bf7eb083e5a9bea6b38c93c0bde0c48f126da7b0cd4838f501207aa047728b9765aad94979ea60bf84868f1230a5ce0753d92d4518318a336ec5a57b225bf6c4f2e729df15a71b3441700e3d8c0626e06861f35ec8eb3eb73dd29155b87d425091e39e5d65a3012d92f0927c22a5136eb185a0c15dccf67a5a35291623df355c5e486bdfa9ef3003c39363c835a331b35026b5dbf9eb9096890def3918ca84221b5850299dd929c8409d0e3165b206aac8aef4689a495acfc76014186628595cd3b0c57bffafc4759cb44eb9c62a111ca1e8c3b7e521e89a7958bdf960dd5d2f58ca4b1b9ed1c31ea6f12965b480743591d8fecb7f560ab0aaf3c7bd1c98a3dd023f7c1c083851c11d677c04e183cf50bd997763b824e2f659fd4f33b48cc1e955a8666e5002ebcf62e1eaddc7d0ccc78bdf93ceeee24f6aa0512a23bb88e16d05ed9493ddf2cd9cf0d2af7ac1e01076fdd3d06ba39b5e6f9f1e4e21be1dee384c2a21f5b9d2cb50d57b9268eb94fd61839c88a63f0a263a5947a3023fdcdb77cedfa153758cfacf95b45331e08c45b185eb16975f7f6493eda95aeda04e2d52b3ececddeba1be5402aa76f680dcafce657e8b1fe25eaf3c4c54b08d4b844492426dd0e892c05ac48459e5677479fc4a1c55faee2e0b353236b962b74bf777e81efcfb2074bb33d8d0b06364bf7c519b5353f6b1c09fb79d44f316090d89039ba2b389c2ab9168b556ee2af88e474438b165e15e894a9a37e22308001423598e4918b07a0bfd9b6ae335d633d713890c6019f4a90fe2ffd4146bcac133f7e5728e81940e8ec21ec8ae4253eef2e7ff5d38c0af311c39b19f4fd78271606e63a39de080235db7a9d63239329c4b1d1f42b0b48d19034363e42b50062889c64bc1f810b0175dd48a25799bc5e7f950c43ea27cac420f1108cc9b502bc525096a5cc2da8a7eca1363cf463b2184d3880f2183ac19362108848bf65d913d625fad1911fe751a4e18ca23042d5c8e3001ea37f4926ccf13e1a4940f857a20485516f085b3b2a5ba11823cd4a8ffe2e5d5588750eab65ee04697d52996fe39bfc804505853904352716392633570cbe399a46bf0dc609773f990d4d407129a5b93659f8680c4431dcd09d91400d2f03a011826c1dd07ff4040a50cdffcf9cde9a53a9bd69a8728bc8a34e1592ce17976c1099471bd6a667a1d264f7e363d2987791bcc10b0d56bc13c092e9ae29ddede1193c3f6ae71c0cc2e76f4e40ea51f74e8724e6320a6dc80781ab3460e46451b7388b4332874c4e8a421ef60f352678f49d7e357d54b970cf2c90244db23fc203bc09240087ef622d08e52be442c03be28c2e8aaf76a1db8cee838e599cbd440e37475cfb910d5148d8f64e951933");


    private final AEAD aead = new AEAD(myKey, otherKey, myIV, otherIV);

    @Test(expected = IllegalArgumentException.class)
    public void myKeyInvalidLength() {
        new AEAD(new byte[11], otherKey, myIV, otherIV);
    }

    @Test(expected = IllegalArgumentException.class)
    public void myKeyNull() {
        new AEAD(null, otherKey, myIV, otherIV);
    }

    @Test(expected = IllegalArgumentException.class)
    public void otherKeyInvalidLength() {
        new AEAD(myKey, new byte[11], myIV, otherIV);
    }

    @Test(expected = IllegalArgumentException.class)
    public void otherKeyNull() {
        new AEAD(myKey, null, myIV, otherIV);
    }

    @Test(expected = IllegalArgumentException.class)
    public void myIVInvalidLength() {
        new AEAD(myKey, otherKey, new byte[11], otherIV);
    }

    @Test(expected = IllegalArgumentException.class)
    public void myIVNull() {
        new AEAD(myKey, otherKey, null, otherIV);
    }

    @Test(expected = IllegalArgumentException.class)
    public void otherIVInvalidLength() {
        new AEAD(myKey, otherKey, myIV, new byte[11]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void otherIVNull() {
        new AEAD(myKey, otherKey, myIV, null);
    }

    @Test
    public void seal() throws GeneralSecurityException {
        aead.seal(plainText, 7, aad);
        aead.seal(plainText, 8, aad);
        aead.seal(plainText, 9, aad);
        byte[] actual = aead.seal(plainText, packetNumber, aad);
        assertArrayEquals(myCipherText, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sealNullAad() throws GeneralSecurityException {
        aead.seal(plainText, packetNumber, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sealNullSrc() throws GeneralSecurityException {
        aead.seal(null, packetNumber, aad);
    }

    @Test
    public void open() throws GeneralSecurityException {
        byte[] actual = aead.open(otherCipherText, packetNumber, aad);
        assertArrayEquals(plainText, actual);
    }

    @Test(expected = AEADBadTagException.class)
    public void openBadAad() throws GeneralSecurityException {
        byte[] actual = aead.open(otherCipherText, packetNumber, new byte[aad.length]);
        assertArrayEquals(plainText, actual);
    }
}